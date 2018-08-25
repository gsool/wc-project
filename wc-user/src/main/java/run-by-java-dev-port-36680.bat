@echo off
title APP-36680

::设置可读的时间戳
::set dateTimeStr=%date:~0,4%-%date:~5,2%-%date:~8,2%T%time:~0,2%:%time:~3,2%:%time:~6,2%

::依赖的项目的根目录
set dependent_project_a_directory=D:/git_workspace/wc-project/wc-all-dubbo-common
::当前项目的根目录
set current_project_directory=D:/git_workspace/wc-project/wc-user

::安装依赖的项目（保证依赖的项目本地仓库安装的版本是最新的）
cd %dependent_project_a_directory%
call mvn clean install

::如果没有下载依赖的JAR，则下载
cd %current_project_directory%
call mvn dependency:copy-dependencies

::设置CLASSPATH，执行此脚本请确认已执行本项目的“export-jar.bat”脚本下载所依赖的JAR
cd %current_project_directory%/target/classes

::设置编译路径
set CLASSPATH=../dependency/*;./classes;%CLASSPATH%

::JAVA参数
set vm_args=
set vm_args=%vm_args% -Xms256m -Xmx256m -Xss512k
::设置提供者直连
set vm_args=%vm_args% -Ddubbo.resolve.file=%current_project_directory%/direct-provider.properties

java %vm_args% com.nicchagil.WcUserApplication