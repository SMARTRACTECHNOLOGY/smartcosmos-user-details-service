FROM smartcosmos/service
MAINTAINER SMART COSMOS Platform Core Team

ENV CONFIG_SERVER_URL 'http://localhost:8888'

ADD target/smartcosmos-*.jar  /opt/smartcosmos/smartcosmos-user-details-service.jar

EXPOSE 8761

CMD ["java", "-jar", "/opt/smartcosmos/smartcosmos-user-details-service.jar"]
