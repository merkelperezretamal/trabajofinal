FROM incodehq/tomcat
RUN rm -rf ${DEPLOYMENT_DIR}/ROOT
COPY project.war ${DEPLOYMENT_DIR}/ROOT.war
EXPOSE 8080