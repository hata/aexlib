/*
 * $Id$
 * 
 * Copyright 2009 Hiroki Ata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aexlib.gae.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.aexlib.gae.tool.anno.Indexable;
import org.aexlib.gae.tool.anno.KeyType;
import org.aexlib.gae.tool.anno.Kind;
import org.aexlib.gae.tool.anno.ParentKind;
import org.aexlib.gae.tool.anno.Property;
import org.aexlib.gae.tool.anno.Version;


public abstract class EntityBaseCodeGenerator {
    private static final String DEFAULT_CHARSET = "utf-8";
    private static final String RET = System.getProperty("line.separator") != null ? System.getProperty("line.separator") : "\n";
    

    // EntityBase.txt
    public static final String PACKAGE = "${package}";
    public static final String ENTITY_BASE_NAME = "${entity.base.name}";
    public static final String ENTITY_FACTORY_TYPE = "${entity.factory.type}";
    public static final String VERSION_INFO_FIELD = "${version.info.field}";
    public static final String VERSION_FIELD = "${version.field}";
    public static final String VERSION_INIT_FIELD = "${version.init.field}";
    public static final String PROPERTY_INFO_FIELDS = "${property.info.fields}";
    public static final String PROPERTY_FIELDS = "${property.fields}";
    public static final String PROPERTY_INIT_FIELDS = "${property.init.fields}";
    public static final String VERSION_MANAGER_FIELD = "${version.manager.field}";
    public static final String VERSION_MANAGER_INIT_FIELD = "${version.manager.init.field}";
    public static final String VERSION_MANAGER_METHODS = "${version.manager.methods}";
    public static final String VERSION_GET_METHOD = "${version.get.method}";
    public static final String PROPERTY_GET_METHODS = "${property.get.methods}";
    public static final String PROPERTY_SET_METHODS = "${property.set.methods}";
    public static final String IMPORT_CLASSES = "${import.classes}";
    public static final String KIND_ANNOTATION = "${kind.annotation}";

    // EntityChildBase.txt
    public static final String ENTITY_BASE_PARENT_NAME = "${entity.base.parent.name}";

    // EntityVersionManagerImpl.txt
    public static final String VERSION_CLASS_NAME = "${version.class.name}";
    public static final String VERSION_NUMBER = "${version.number}";
    public static final String VERSION_IDENTIFIER = "${version.identifier}";

    // PropertyField.txt
    public static final String PROPERTY_TYPE = "${property.type}";
    public static final String PROPERTY_NAME_LOWERCASE = "${property.name.lowercase}";

    // PropertyInfoField.txt
    public static final String PROPERTY_NAME = "${property.name}";
    public static final String PROPERTY_NAME_UPPERCASE = "${property.name.uppercase}";
    public static final String PROPERTY_INDEXABLE = "${property.indexable}";
    
    // PropertyInitField.txt
    // VersionField.txt
    public static final String VERSION_NAME_LOWERCASE = "${version.name.lowercase}";
    
    // VersionInfoField.txt
    public static final String VERSION_NAME_UPPERCASE = "${version.name.uppercase}";
    public static final String VERSION_NAME = "${version.name}";
    // VersionInitField.txt

    // VersionManagerField.txt
    public static final String VERSION_MANAGER_INIT_INSTANCE = "${version.manager.init.instances}";
    // VersionManagerMethods.txt
    
    public static final String VERSION_NAME_CAPITAL = "${version.name.capital}";
    public static final String PROPERTY_NAME_CAPITAL = "${property.name.capital}";
    
    public static final String COLLECTION_TYPE = "${collection.type}";
    
    public static final String KEY_LINK_TYPE = "${key.link.type}";
    
    private static final String DEFAULT_FACTORY_TYPE = "Name";
    
    private static enum Template {
            EntityBase,
            EntityChildBase,
            EntityVersionManagerImpl,
            PropertyField,
            PropertyInfoField,
            PropertyInitField,
            PropertyGetMethod,
            PropertySetMethod,
            VersionClassName,
            VersionField,
            VersionInfoField,
            VersionInitField,
            VersionGetMethod,
            VersionManagerField,
            VersionManagerInitField,
            VersionManagerMethods,
            CollectionPropertyField,
            CollectionPropertyInfoField,
            CollectionPropertyGetMethod,
            CollectionPropertySetMethod,
            KeyLinkPropertyInfoField,
            KeyLinkCollectionPropertyInfoField,
    };
    
    
    private Set<Class<?>> collectionTypeSet = new HashSet<Class<?>>();

    private Map<Template, EntityTemplate> templateMap;
    
    private String entityClassesPath;
    private String versionClassesPath;

    private static final InvocationHandler handler = new NullInvocationHandler();

    private void logMessage(String message) {
        System.out.println(message);
    }
    
    public void generate() throws IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        initCollectionTypeSet();
        loadTemplate();
        createPackageDir(getOutputSourceDir());

        for (Class<?> entityDef : getEntityDefinitions()) {
            logMessage("Generating for " + entityDef.getSimpleName());
            Map<String, String> tokenMap = new HashMap<String, String>(getTokenToKeywordMap());
            generateEntityCode(entityDef, tokenMap);
        }
        
        logMessage("Done.");
    }
    
    protected abstract File getOutputSourceDir() throws IOException;

    protected abstract String getGeneratedClassPackage();
    
    protected abstract Class<?>[] getEntityDefinitions();
    

    protected void loadTemplate() throws IOException {
        logMessage("loadTemplate...");
        templateMap = new HashMap<Template, EntityTemplate>();
        for (Template template : Template.values()) {
            templateMap.put(template, new EntityTemplate(template.toString() + ".txt"));
        }
    }
    
    protected void createPackageDir(File outputDir) throws IOException {
        logMessage("Create package directory...");

        entityClassesPath = outputDir.getCanonicalPath() + File.separator +
            getGeneratedClassPackage().replaceAll("\\.", File.separator);
        versionClassesPath = entityClassesPath + File.separator + "version";

        File f = new File(versionClassesPath);
        if (!f.exists() && !f.mkdirs()) {
            throw new IOException("Cannot create a new directory: " + f.getCanonicalPath());
        } else {
            logMessage("Created " + f);
        }
    }
    
    protected Map<String, String> getTokenToKeywordMap() {
        HashMap<String, String> initMap = new HashMap<String, String>();
        // Init defaults.
        initMap.put(IMPORT_CLASSES, "");
        initMap.put(KIND_ANNOTATION, "");
        initMap.put(VERSION_FIELD, "");
        initMap.put(VERSION_INIT_FIELD, "");
        initMap.put(VERSION_INFO_FIELD, "");
        initMap.put(VERSION_GET_METHOD, "");

        return initMap;
    }
    
    private void generateEntityCode(Class<?> entityDef, Map<String, String> tokenMap) throws IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InstantiationException {

        generateEntityClassName(entityDef, tokenMap);
        generateProperties(entityDef, tokenMap);
        generateVersionManagers(entityDef, tokenMap);
        
        String code = tokenMap.get(ENTITY_BASE_PARENT_NAME) != null ?
                templateMap.get(Template.EntityChildBase).apply(tokenMap) :
                    templateMap.get(Template.EntityBase).apply(tokenMap);

        File path = new File(entityClassesPath + File.separator + tokenMap.get(ENTITY_BASE_NAME) + ".java");
        writeToFile(path, code);
    }

    private void generateEntityClassName(Class<?> entityDef, Map<String, String> tokenMap) {
        tokenMap.put(PACKAGE, getGeneratedClassPackage());
        Kind kind = entityDef.getAnnotation(Kind.class);
        tokenMap.put(ENTITY_BASE_NAME, /*kind != null ?  kind.value().trim() :*/ entityDef.getSimpleName());
        if (kind != null) {
            String imports = tokenMap.get(IMPORT_CLASSES);
            tokenMap.put(IMPORT_CLASSES, imports + "import org.aexlib.gae.datastore.anno.Kind;" + RET);
            tokenMap.put(KIND_ANNOTATION, "@Kind(\"" + kind.value().trim() + "\")");
        }
        ParentKind parentKind = entityDef.getAnnotation(ParentKind.class);
        if (parentKind != null) {
            Class<?> parentEntityDefClass = parentKind.value();
            Kind parentEntityDefKind = parentEntityDefClass.getAnnotation(Kind.class);
            tokenMap.put(ENTITY_BASE_PARENT_NAME, /*parentEntityDefKind != null ? parentEntityDefKind.value().trim() :*/ parentEntityDefClass.getSimpleName());
        }
        KeyType factory = entityDef.getAnnotation(KeyType.class);
        tokenMap.put(ENTITY_FACTORY_TYPE, factory != null ? factory.value().toString() : DEFAULT_FACTORY_TYPE);
    }
    
    private void generateProperties(Class<?> entityDef, Map<String, String> tokenMap) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        StringBuilder initsBuilder = new StringBuilder();
        StringBuilder infosBuilder = new StringBuilder();
        StringBuilder fieldsBuilder = new StringBuilder();
        StringBuilder getMethodsBuilder = new StringBuilder();
        StringBuilder setMethodsBuilder = new StringBuilder();
        
        for (Field field : entityDef.getDeclaredFields()) {
            final Version version = field.getAnnotation(Version.class);
            if (version != null) {
                final String versionName = getPropertyName(field);
                setVersionName(versionName, tokenMap);
                tokenMap.put(VERSION_FIELD, templateMap.get(Template.VersionField).apply(tokenMap));
                tokenMap.put(VERSION_INIT_FIELD, templateMap.get(Template.VersionInitField).apply(tokenMap));

                field.setAccessible(true);
                Object proxy = getInstance(entityDef);
                Object defaultVersionNumber = field.get(proxy);
                if (defaultVersionNumber == null) {
                    tokenMap.put(VERSION_NUMBER, "1");
                } else {
                    tokenMap.put(VERSION_NUMBER, defaultValueToString(defaultVersionNumber));
                }
                tokenMap.put(VERSION_INFO_FIELD, templateMap.get(Template.VersionInfoField).apply(tokenMap));
                tokenMap.put(VERSION_GET_METHOD, templateMap.get(Template.VersionGetMethod).apply(tokenMap));
            } else {
                String propertyName = getPropertyName(field);
                setPropertyName(propertyName, tokenMap);
                Class<?> fieldClass = field.getType();
                boolean isKeyLink = false;

                if (field.getAnnotation(Indexable.class) != null) {
                    tokenMap.put(PROPERTY_INDEXABLE, "Indexable");
                } else {
                    tokenMap.put(PROPERTY_INDEXABLE, "");
                }

                if (isSupportedCollectionType(fieldClass)) {
                    Type fieldType = field.getGenericType();
                    if (fieldType instanceof ParameterizedType) {
                        ParameterizedType type = (ParameterizedType)fieldType;
                        Type[] typeArguments = type.getActualTypeArguments();
                        for (Type paramType : typeArguments) {
                            Class<?> paramClass = (Class<?>)paramType;
                            if (fieldClass.getPackage().equals("java.lang")) {
                                tokenMap.put(PROPERTY_TYPE, paramClass.getSimpleName());
                            } else {
                                tokenMap.put(PROPERTY_TYPE, paramClass.getName());
                            }
                            isKeyLink = isKeyLink(paramClass);
                            if (isKeyLink) {
                                setKeyLinkValue(paramClass, tokenMap);
                            }
                            break;
                        }
                    }
                    if (fieldClass.getPackage().equals("java.lang")) {
                        tokenMap.put(COLLECTION_TYPE, fieldClass.getSimpleName());
                    } else {
                        tokenMap.put(COLLECTION_TYPE, fieldClass.getName());
                    }
                    initsBuilder.append(templateMap.get(Template.PropertyInitField).apply(tokenMap)).append(RET);
                    if (isKeyLink) {
                        infosBuilder.append(templateMap.get(Template.KeyLinkCollectionPropertyInfoField).apply(tokenMap)).append(RET);
                    } else {
                        infosBuilder.append(templateMap.get(Template.CollectionPropertyInfoField).apply(tokenMap)).append(RET);
                    }
                    fieldsBuilder.append(templateMap.get(Template.CollectionPropertyField).apply(tokenMap)).append(RET);
                    getMethodsBuilder.append(templateMap.get(Template.CollectionPropertyGetMethod).apply(tokenMap));
                    setMethodsBuilder.append(templateMap.get(Template.CollectionPropertySetMethod).apply(tokenMap));
                } else {
                    // It may be better to use import.
                    if (fieldClass.getPackage().equals("java.lang")) {
                        tokenMap.put(PROPERTY_TYPE, fieldClass.getSimpleName());
                    } else {
                        tokenMap.put(PROPERTY_TYPE, fieldClass.getName());
                    }
                    isKeyLink = isKeyLink(fieldClass);
                    if (isKeyLink) {
                        setKeyLinkValue(fieldClass, tokenMap);
                    }
                    initsBuilder.append(templateMap.get(Template.PropertyInitField).apply(tokenMap)).append(RET);
                    if (isKeyLink) {
                        infosBuilder.append(templateMap.get(Template.KeyLinkPropertyInfoField).apply(tokenMap)).append(RET);
                    } else {
                        infosBuilder.append(templateMap.get(Template.PropertyInfoField).apply(tokenMap)).append(RET);
                    }
                    fieldsBuilder.append(templateMap.get(Template.PropertyField).apply(tokenMap)).append(RET);
                    getMethodsBuilder.append(templateMap.get(Template.PropertyGetMethod).apply(tokenMap));
                    setMethodsBuilder.append(templateMap.get(Template.PropertySetMethod).apply(tokenMap));
                }

            }
        }

        tokenMap.put(PROPERTY_INIT_FIELDS, initsBuilder.toString());
        tokenMap.put(PROPERTY_INFO_FIELDS, infosBuilder.toString());
        tokenMap.put(PROPERTY_FIELDS, fieldsBuilder.toString());
        tokenMap.put(PROPERTY_GET_METHODS, getMethodsBuilder.toString());
        tokenMap.put(PROPERTY_SET_METHODS, setMethodsBuilder.toString());
    }
    
    private void setVersionName(String name, Map<String, String> tokenMap) {
        tokenMap.put(VERSION_NAME, name);
        tokenMap.put(VERSION_NAME_LOWERCASE, toFieldPropertyName(name));
        tokenMap.put(VERSION_NAME_UPPERCASE, toFieldPropertyInfoName(name));
        tokenMap.put(VERSION_NAME_CAPITAL, toCapitalize(name));
    }
    
    private void setPropertyName(String name, Map<String, String> tokenMap) {
        tokenMap.put(PROPERTY_NAME, name);
        tokenMap.put(PROPERTY_NAME_LOWERCASE, toFieldPropertyName(name));
        tokenMap.put(PROPERTY_NAME_UPPERCASE, toFieldPropertyInfoName(name));
        tokenMap.put(PROPERTY_NAME_CAPITAL, toCapitalize(name));
    }

    private String toCapitalize(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    private String defaultValueToString(Object o) {
        return o.toString(); // TODO: This should change for some objects.
    }
    

    private void generateVersionManagers(Class<?> entityDef, Map<String, String> tokenMap) throws IOException {
//        tokenMap.put(VERSION_IDENTIFIER, getVersionIdentifier(entityDef, tokenMap));
        tokenMap.put(VERSION_CLASS_NAME, templateMap.get(Template.VersionClassName).apply(tokenMap).trim());

        // If there is a token for version number, generate a version manager code.
        // Otherwise, skip this step.
        if (tokenMap.get(VERSION_NUMBER) != null) {
            generateVersionManagerCode(tokenMap);
        }

        StringBuilder builder = new StringBuilder();
        for (String className : getVersionManagerList(tokenMap.get(ENTITY_BASE_NAME))) {
            builder.append("        VERSION_MANAGERS.add(").append(className).append(".newInstance());").append(RET);
        }
        if (builder.length() > 0) {
            tokenMap.put(VERSION_MANAGER_INIT_INSTANCE, builder.toString());
            tokenMap.put(VERSION_MANAGER_INIT_FIELD, templateMap.get(Template.VersionManagerInitField).apply(tokenMap));
            tokenMap.put(VERSION_MANAGER_FIELD, templateMap.get(Template.VersionManagerField).apply(tokenMap));
            tokenMap.put(VERSION_MANAGER_METHODS, templateMap.get(Template.VersionManagerMethods).apply(tokenMap));
        } else {
            tokenMap.put(VERSION_MANAGER_INIT_INSTANCE, "");
            tokenMap.put(VERSION_MANAGER_INIT_FIELD, "");
            tokenMap.put(VERSION_MANAGER_FIELD, "");
            tokenMap.put(VERSION_MANAGER_METHODS, "");
        }
    }

    private java.util.List<String> getVersionManagerList(final String entityBaseName) {
        File f = new File(versionClassesPath);
        String[] classNames = f.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(entityBaseName + "_ver") && name.endsWith(".java");
            }});

        ArrayList<String> classNameList = new ArrayList<String>(classNames.length);
        for (int i = 0;i < classNames.length;i++) {
            classNameList.add(classNames[i].substring(0, classNames[i].length() - ".java".length()));
        }

        return classNameList;
    }
    
    /*
    private String getVersionIdentifier(Class<?> entityDef, Map<String, String> tokenMap) {
        ArrayList<String> elements = new ArrayList<String>();

        // kind name
        elements.add(tokenMap.get(ENTITY_BASE_NAME));

        // Parent kind name.
        elements.add(tokenMap.get(ENTITY_BASE_PARENT_NAME));

        // Factory type.
        elements.add(tokenMap.get(ENTITY_FACTORY_TYPE));

        // Fields type, name, and property.
        Field[] fields = entityDef.getFields();
        Arrays.sort(fields, new Comparator<Field>() {
            public int compare(Field o1, Field o2) {
                return getPropertyName(o1).compareTo(getPropertyName(o2));
            }
        });
        for (Field field : fields) {
            elements.add(field.getType() + " " + field.getName() + " ver=" + isVersionField(field));
        }

        StringBuilder builder = new StringBuilder();
        for (String element : elements) {
            builder.append(element + "\n");
        }

        return toIdentifier(builder.toString());
    }
*/    
    private void generateVersionManagerCode(Map<String, String> tokenMap) throws IOException {
        File versionClassPath = new File(versionClassesPath + File.separator + tokenMap.get(VERSION_CLASS_NAME) + ".java");

        // Don't overwrite existing versionmanager.
        if (!versionClassPath.exists()) {
            writeToFile(versionClassPath, templateMap.get(Template.EntityVersionManagerImpl).apply(tokenMap));
        }
    }

    private void writeToFile(File path, String contents) throws IOException {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(path);
            Writer writer = new OutputStreamWriter(fout, DEFAULT_CHARSET);
            writer.write(contents);
            writer.flush();
        } finally {
            if (fout != null) {
                fout.close();
            }
        }
    }
    
    private String getPropertyName(Field field) {
        String propertyName = toCapitalize(field.getName());
        Version version = field.getAnnotation(Version.class);
        Property property = field.getAnnotation(Property.class);

        if (version != null) {
            String value = version.value();
            if (value != null) {
                propertyName = value;
            }
        } else if (property != null) {
            String value = property.value();
            if (value != null) {
                propertyName = value;
            }
        }
        
        return propertyName;
    }
/*    
    private boolean isVersionField(Field field) {
        return field.getAnnotation(Version.class) != null;
    }
    
    private String toIdentifier(String s) {
        return ("" + s.hashCode()).replace('-', 'm');
    }
*/
    
    private void initCollectionTypeSet() {
        collectionTypeSet.add(List.class);
        collectionTypeSet.add(Set.class);
        collectionTypeSet.add(SortedSet.class);
    }


    //http://code.google.com/intl/ja/appengine/docs/java/datastore/dataclasses.html
    private boolean isSupportedCollectionType(Class<?> verifiedClass) {
        if (collectionTypeSet.contains(verifiedClass)) {
            return true;
        } else {
            Class<?>[] interfaces = verifiedClass.getInterfaces();
            if (interfaces.length > 0) {
                for (int i = 0;i < interfaces.length;i++) {
                    if (isSupportedCollectionType(interfaces[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    
    private boolean isKeyLink(Class<?> verifiedClass) {
        return verifiedClass.getAnnotation(KeyType.class) != null;
    }
    
    private void setKeyLinkValue(Class<?> linkClass, Map<String, String> tokenMap) {
        HashMap<String, String> linkMap = new HashMap<String, String>();
        generateEntityClassName(linkClass, linkMap);
        tokenMap.put(KEY_LINK_TYPE, linkMap.get(ENTITY_BASE_NAME));
        tokenMap.put(PROPERTY_TYPE, linkMap.get(ENTITY_BASE_NAME));
    }

    // Change name from AxxxBxxx to axxxBxxx.
    private String toFieldPropertyName(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
    }

    // Change from nameSub to NAME_SUB
    private String toFieldPropertyInfoName(String name) {
        StringBuffer buff = new StringBuffer();
        boolean isPreviousLowerCase = false;
        for (int i = 0;i < name.length();i++) {
            char c = name.charAt(i);
            if (isPreviousLowerCase && Character.isUpperCase(c)) {
                buff.append("_");
                isPreviousLowerCase = false;
            } else if (Character.isLowerCase(c)) {
                isPreviousLowerCase = true;
            } else if (Character.isUpperCase(c)) {
                isPreviousLowerCase = false;
            }
            buff.append(Character.toUpperCase(c));
        }
        return buff.toString();
    }

    public static <DEFINITION> DEFINITION getInstance(Class<DEFINITION> entityDefinition) throws InstantiationException, IllegalAccessException {
        if (entityDefinition.isInterface()) {
            return entityDefinition.cast(Proxy.newProxyInstance(entityDefinition.getClassLoader(),
                    new Class[] { entityDefinition }, handler));
        } else {
            return entityDefinition.newInstance();
        }
    }
    
    private static class NullInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            return null;
        }
        
    }

}
