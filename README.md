# Druid infra setting
- druid 실행을 위한 docker-compose 파일 및 환경설정 파일 작성
    - 환경 구성
        - druid(zookeeper, doordinator, broker, historical, middlemanager, router) + minio(deep storage) + mysql(meta storage)
    - 포트 설정
        |service|PORT|
        |------|---|
        |minio|9000|
        |druid-metadata-storage|10035|
        |druid-zookeeper|10036|
        |druid-coordinator|10037|
        |druid-broker|10038|
        |druid-historical|10039|
        |druid-middlemanager|10040|
        |druid-router|10050|

- ingestion, sql 에 대한 테스트 진행


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

<br>

# [ MySQL meta storage 설정 ]
기존 apache/druid:0.22.1에 mysql metastore 적용 시, mysql-connector jar file을 찾을 수 없다는 에러 발생.

**다음과 같이 해결**
참고: [druid 공식 repository](https://github.com/apache/druid/tree/master/distribution/docker)


``` 
docker build -t apache/druid:0.22.1-mysql --build-arg DRUID_RELEASE=apache/druid:0.22.1 -f ./infra/druid/Dockerfile.mysql . 
```
<img width="700" alt="image" src="https://user-images.githubusercontent.com/75469281/177033596-e230060a-4cc8-441a-9e3d-c29127ca3583.png">

![druid 0 22 1-mysql](https://user-images.githubusercontent.com/75469281/177033612-b76433ef-721d-4b3a-8a21-8cfbd92d4156.png)

<br>

# [ druid component 개별 environment 적용 ]
1. docker-compose.yml 과 같은 디렉토리에 env-configs/{component역할}/{environment_file} 환경 설정
2. environment_file 이름은 druid component 이름과 동일

<img width="200" alt="image" src="https://user-images.githubusercontent.com/75469281/177033787-275cb8de-8c34-4ca7-a224-e9f661e56974.png">

<br>

# [ deep storage S3 연결 ]
1. 데이터 로드 from s3
2. deep storage 설정 for s3
3. test code
- s3에서 데이터 로드 후 spec을 이용하여 druid task 생성
- 생성된 druid에 쿼리

## [ load data from s3 ]
<img width="600" alt="image" src="https://user-images.githubusercontent.com/75469281/177033848-f8d2b6ab-bb90-46f8-9146-b1fad30ce4e6.png">

<img width="600" alt="image" src="https://user-images.githubusercontent.com/75469281/177033856-24e6f0ff-b738-4be2-b53e-5415dfa541bc.png">


