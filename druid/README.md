# Druid infra setting
- druid 환경 세팅을 위한 테스트 디렉토리

## 작업 내용
- druid 실행을 위한 docker-compose 파일 및 환경설정 파일 작성
    - 환경 구성
        - druid(zookeeper, doordinator, broker, historical, middlemanager, router) + minio(deep storage) + mysql(meta storage)
    - 포트 설정
        - minio : 9000
        - druid-metadata-storage(mysql) : 10035
        - druid-zookeeper : 10036
        - druid-coordinator : 10037
        - druid-broker : 10038
        - druid-historical : 10039
        - druid-middlemanager : 10040
        - druid-router : 10050
- dacecntro에서 사용될 기능(ingestion, sql)들에 대한 테스트 진행


## 구동 확인
- 도커 실행
```bash
docker-compose up -d

# druid 구동 확인
http://{install_server_host}:10050

# minio web ui 접속
http://{install_server_host}:9000
```

- 테스트 실행
```bash
# 테스트 프로젝트로 이동
cd sample

# 테스트 실행
./gradlew test --info
```

## TODO
- [X] meta storage 설정
- [X] meta storage volume 분리
- [X] deep storage 설정
- [X] devenv로 통합
