package com.sws.myGenerator.codegen.mybatis.javamapper;

import com.sws.myGenerator.api.MyIntrospectedTable;
import io.swagger.annotations.ApiOperation;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class JavaServiceGenerator extends AbstractJavaGenerator {

    public JavaServiceGenerator(){
        super();
    }
    public boolean isSimple = true;

    public String daoTypeName = "";

    @Override
    public List<CompilationUnit> getCompilationUnits() {


        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((MyIntrospectedTable)introspectedTable).getServiceType());
        //添加到answer里的话会创建class类  ，但是不能删掉，有点鸡肋。删掉的话selectall中的方法
        //context.getPlugins().modelExampleClassGenerated(topLevelClass1, introspectedTable)会报错   到时候看看能不能改
        TopLevelClass topLevelClass1 = new TopLevelClass(type);
        //添加到answer里的话会创建接口类
        Interface topLevelClass = new Interface(type);

        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass1.setVisibility(JavaVisibility.PUBLIC);
        //topLevelClass.addAnnotation("@Service");
        //topLevelClass.addImportedType("org.springframework.stereotype.Service");

        Method method = new Method();

//        Field field = new Field();
//        field.setVisibility(JavaVisibility.PROTECTED);
//        FullyQualifiedJavaType daoType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
//        daoTypeName = daoType.getShortName().substring(0, 1).toLowerCase()+daoType.getShortName().substring(1);
//        field.setType(daoType);
//        field.setName(daoTypeName);
//        field.addAnnotation("@Autowired");
//        topLevelClass.addField(field);
//        topLevelClass.addImportedType(daoType);
//        topLevelClass.addImportedType("org.springframework.beans.factory.annotation.Autowired");


        //增加
        method = new Method();
        //method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(introspectedTable.getInsertStatementId());
        FullyQualifiedJavaType parameterType1;
        if (isSimple) {
            parameterType1 = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        } else {
            parameterType1 = introspectedTable.getRules().calculateAllFieldsClass();
        }
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(parameterType1);
        method.addParameter(new Parameter(parameterType1, "entity"));
        //method.addBodyLine("return "+daoTypeName+"."+introspectedTable.getInsertStatementId()+"(entity);");
        topLevelClass.addMethod(method);
        topLevelClass1.addMethod(method);


        //删除
        method = new Method();

        //@ApiOperation(value = "通过id删除", notes = "通过id删除")
        //method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(introspectedTable.getDeleteByPrimaryKeyStatementId());
        StringBuilder sb = new StringBuilder();
        if (!isSimple && introspectedTable.getRules().generatePrimaryKeyClass()) {
            FullyQualifiedJavaType type2 = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
            importedTypes.add(type2);
            method.addParameter(new Parameter(type2, "key"));
            sb.append("key");
        } else {
            List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
            for (IntrospectedColumn introspectedColumn : introspectedColumns) {
                FullyQualifiedJavaType type3 = introspectedColumn.getFullyQualifiedJavaType();
                importedTypes.add(type3);
                Parameter parameter = new Parameter(type3, introspectedColumn.getJavaProperty());
                if(sb.toString().length()>0){
                    sb.append(",");
                }
                sb.append(introspectedColumn.getJavaProperty());
                method.addParameter(parameter);
            }
        }
        //method.addBodyLine("return "+daoTypeName+"."+introspectedTable.getDeleteByPrimaryKeyStatementId()+"("+sb.toString()+");");
        topLevelClass.addMethod(method);
        topLevelClass1.addMethod(method);

        //修改

        FullyQualifiedJavaType parameterType2;
        if (this.introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType2 = new FullyQualifiedJavaType(this.introspectedTable.getRecordWithBLOBsType());
        } else {
            parameterType2 = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        }
        importedTypes.add(parameterType2);
        method = new Method();
        //method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(introspectedTable.getUpdateByPrimaryKeyStatementId());
        method.addParameter(new Parameter(parameterType2, "record"));
        //method.addBodyLine("return "+daoTypeName+"."+introspectedTable.getUpdateByPrimaryKeyStatementId()+"(record);");
        topLevelClass.addMethod(method);
        topLevelClass1.addMethod(method);

        //查询
        method = new Method();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        //method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType;
        listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        importedTypes.add(listType);
        returnType.addTypeArgument(listType);

        method.setReturnType(returnType);
        method.setName(introspectedTable.getSelectAllStatementId());
        //method.addBodyLine("return "+daoTypeName+"."+introspectedTable.getSelectAllStatementId()+"();");
        topLevelClass.addMethod(method);
        topLevelClass.addImportedTypes(importedTypes);

        topLevelClass1.addMethod(method);
        topLevelClass1.addImportedTypes(importedTypes);


        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(topLevelClass1, introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }

}
