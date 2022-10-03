package druid

import app.domain.druid.configuration.DruidConfiguration
import druid.sample.JdbcRunner
import druid.sample.RestRunner
import java.lang.Exception
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class SampleApplicationTests(
    @Autowired
    private val druidConfiguration: DruidConfiguration,
    private val restRunner: RestRunner = RestRunner(),
    private val jdbcRunner: JdbcRunner = JdbcRunner(druidConfiguration)
){
    companion object {
        private val datasourceName: String = "HP_${System.currentTimeMillis()}"
        private lateinit var taskId: String
        private const val DRUID_S3_ACCESS_KEY_ID = "minio"
        private const val DRUID_S3_ACCESS_SECRET_KEY = "minio_admin"
    }

    @Test
    @Order(1)
    @DisplayName("Task 생성 - S3")
    fun runTaskWithS3FileTest() {
        val spec = """
            {
              "type": "index_parallel",
              "spec": {
                "ioConfig": {
                  "type": "index_parallel",
                  "inputSource": {
                    "type": "s3",
                    "uris": [
                      "s3://dataset/test_data/house_prise.csv"
                    ],
                    "properties": {
                      "accessKeyId": "$DRUID_S3_ACCESS_KEY_ID",
                      "secretAccessKey": "$DRUID_S3_ACCESS_SECRET_KEY"
                    }
                  },
                  "inputFormat": {
                    "type": "csv",
                    "findColumnsFromHeader": true
                  }
                },
                "tuningConfig": {
                  "type": "index_parallel",
                  "partitionsSpec": {
                    "type": "dynamic"
                  }
                },
                "dataSchema": {
                  "dataSource": "$datasourceName",
                  "timestampSpec": {
                    "column": "YrSold",
                    "format": "yyyy"
                  },
                  "transformSpec": {},
                  "dimensionsSpec": {
                    "dimensions": [
                      {
                        "type": "long",
                        "name": "Id"
                      },
                      {
                        "type": "long",
                        "name": "MSSubClass"
                      },
                      "MSZoning",
                      "LotFrontage",
                      {
                        "type": "long",
                        "name": "LotArea"
                      },
                      "Street",
                      "Alley",
                      "LotShape",
                      "LandContour",
                      "Utilities",
                      "LotConfig",
                      "LandSlope",
                      "Neighborhood",
                      "Condition1",
                      "Condition2",
                      "BldgType",
                      "HouseStyle",
                      {
                        "type": "long",
                        "name": "OverallQual"
                      },
                      {
                        "type": "long",
                        "name": "OverallCond"
                      },
                      {
                        "type": "long",
                        "name": "YearBuilt"
                      },
                      {
                        "type": "long",
                        "name": "YearRemodAdd"
                      },
                      "RoofStyle",
                      "RoofMatl",
                      "Exterior1st",
                      "Exterior2nd",
                      "MasVnrType",
                      {
                        "type": "long",
                        "name": "MasVnrArea"
                      },
                      "ExterQual",
                      "ExterCond",
                      "Foundation",
                      "BsmtQual",
                      "BsmtCond",
                      "BsmtExposure",
                      "BsmtFinType1",
                      {
                        "type": "long",
                        "name": "BsmtFinSF1"
                      },
                      "BsmtFinType2",
                      {
                        "type": "long",
                        "name": "BsmtFinSF2"
                      },
                      {
                        "type": "long",
                        "name": "BsmtUnfSF"
                      },
                      {
                        "type": "long",
                        "name": "TotalBsmtSF"
                      },
                      "Heating",
                      "HeatingQC",
                      "CentralAir",
                      "Electrical",
                      {
                        "type": "long",
                        "name": "1stFlrSF"
                      },
                      {
                        "type": "long",
                        "name": "2ndFlrSF"
                      },
                      {
                        "type": "long",
                        "name": "LowQualFinSF"
                      },
                      {
                        "type": "long",
                        "name": "GrLivArea"
                      },
                      {
                        "type": "long",
                        "name": "BsmtFullBath"
                      },
                      {
                        "type": "long",
                        "name": "BsmtHalfBath"
                      },
                      {
                        "type": "long",
                        "name": "FullBath"
                      },
                      {
                        "type": "long",
                        "name": "HalfBath"
                      },
                      {
                        "type": "long",
                        "name": "BedroomAbvGr"
                      },
                      {
                        "type": "long",
                        "name": "KitchenAbvGr"
                      },
                      "KitchenQual",
                      {
                        "type": "long",
                        "name": "TotRmsAbvGrd"
                      },
                      "Functional",
                      {
                        "type": "long",
                        "name": "Fireplaces"
                      },
                      "FireplaceQu",
                      "GarageType",
                      {
                        "type": "long",
                        "name": "GarageYrBlt"
                      },
                      "GarageFinish",
                      {
                        "type": "long",
                        "name": "GarageCars"
                      },
                      {
                        "type": "long",
                        "name": "GarageArea"
                      },
                      "GarageQual",
                      "GarageCond",
                      "PavedDrive",
                      {
                        "type": "long",
                        "name": "WoodDeckSF"
                      },
                      {
                        "type": "long",
                        "name": "OpenPorchSF"
                      },
                      {
                        "type": "long",
                        "name": "EnclosedPorch"
                      },
                      {
                        "type": "long",
                        "name": "3SsnPorch"
                      },
                      {
                        "type": "long",
                        "name": "ScreenPorch"
                      },
                      {
                        "type": "long",
                        "name": "PoolArea"
                      },
                      "PoolQC",
                      "Fence",
                      "MiscFeature",
                      {
                        "type": "long",
                        "name": "MiscVal"
                      },
                      {
                        "type": "long",
                        "name": "MoSold"
                      },
                      {
                        "type": "long",
                        "name": "YrSold"
                      },
                      "SaleType",
                      "SaleCondition",
                      {
                        "type": "long",
                        "name": "SalePrice"
                      }
                    ]
                  },
                  "granularitySpec": {
                    "queryGranularity": "year",
                    "rollup": false,
                    "segmentGranularity": "year"
                  }
                }
              }
            }
    """.trimIndent()
        taskId = restRunner.runTask(spec)
        assertThat(taskId).isInstanceOf(String::class.java)
    }

    @Test
    @Order(2)
    @DisplayName("Ingestion 완료 대기")
    fun waitForIngestion(){
        val start = System.currentTimeMillis()
        var status = restRunner.getTaskStatus(taskId)
        while (status == "RUNNING"){
            if (System.currentTimeMillis() - start > 600000) break
            println("task $taskId still running ..." )
            Thread.sleep(5000)
            status = restRunner.getTaskStatus(taskId)
        }

        if (status == "RUNNING") throw Exception("Time out")
        else if (status == "FAILED") throw Exception("Task failed")
        else {
            println("task $taskId finish")
            Thread.sleep(60000)
        }
    }

    @Test
    @Order(3)
    @DisplayName("쿼리 실행")
    fun runQueryTestBigint(){
        val sql = """
            select
              EXTRACT(year from __time) as "Year",
              avg(SalePrice) as "AVG Prise"
            from $datasourceName
            group by EXTRACT(year from __time)
            order by EXTRACT(year from __time)
        """.trimIndent()
        val actual = jdbcRunner.runQuery(sql)
        val expected = listOf(
            mapOf("Year" to 2006, "AVG Prise" to 182549L),
            mapOf("Year" to 2007, "AVG Prise" to 186063L),
            mapOf("Year" to 2008, "AVG Prise" to 177360L),
            mapOf("Year" to 2009, "AVG Prise" to 179432L),
            mapOf("Year" to 2010, "AVG Prise" to 177393L)
        )
        val resKeys = listOf("Year", "AVG Prise")
        actual.zip(expected){ a, e ->
            resKeys.forEach{ k ->
                assertThat(a).containsKey(k)
                assertThat(a[k]).isEqualTo(e[k])
            }
        }
    }
}
