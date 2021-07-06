FROM registry.cn-hangzhou.aliyuncs.com/softlab/java11-skywalking-agent:v1
# Do not copy stubs jar
ARG JAR_FILE=target/*[^s].jar 
COPY ${JAR_FILE} /app/app.jar
ENV SW_AGENT_NAME=admin-auth-service
ENTRYPOINT ["java", "-javaagent:/skywalking/agent/skywalking-agent.jar","-jar","/app/app.jar"]