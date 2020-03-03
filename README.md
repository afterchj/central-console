# window系统添加开机启动方法
1. win+r输入gpedit.msc->选择window设置...
2. win+r输入shell:startup->把脚本命令文件拖到该目录  
# 启动方式
1 jar形式
>1.1 修改pom.xml打包方式：  ```<packaging>jar</packaging>```  
>1.2 注释tomcat依赖  

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
>2.打包：mvn package
>3.运行：java -jar xx.jar

2.war形式
>1.1 修改pom.xml打包方式：```<packaging>war</packaging>```  
>1.2 添加以下依赖包覆盖内嵌的tomcat

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
>1.3 修改```server.port```的端口号为实际tomcat的端口号  
>2.打包：mvn clean package -Dmaven.test.skip=true
>3.将生成的war包文件丢到tomcat的webapp目录下启动tomcat  

