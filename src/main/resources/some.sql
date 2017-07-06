-- lis_report
CREATE SEQUENCE LIS_REPORT_AUTO_ID
MINVALUE 1
MAXVALUE 9999999999999999999999999999
START WITH 61
INCREMENT BY 1
CACHE 20;

CREATE TABLE LIS_REPORT
(
  id                  NUMBER NOT NULL,
  test_no             VARCHAR2(64),
  print_order         VARCHAR2(64),
  item_no             VARCHAR2(64),
  report_item_code    VARCHAR2(128),
  report_item_code2   VARCHAR2(128),
  abnormal_indicator  VARCHAR2(1),
  result_date_time    DATE,
  germ_or_normal      VARCHAR2(1),
  action_details      CLOB,
  action_details_type VARCHAR2(8),
  action_date         DATE,
  action_type         VARCHAR2(8)
);
CREATE INDEX ITEM_NO
  ON LIS_REPORT (ITEM_NO);
CREATE INDEX PRINT_ORDER
  ON LIS_REPORT (PRINT_ORDER);
CREATE INDEX TEST_NO
  ON LIS_REPORT (TEST_NO);
ALTER TABLE LIS_REPORT
  ADD CONSTRAINT ID PRIMARY KEY (ID);

CREATE TABLE
  t_mnis_his_dict
(
  col_id        NUMBER,
  col_name      VARCHAR2(256),
  col_code      NUMBER,
  col_type      NUMBER,
  col_user      VARCHAR2(128),
  col_date_time DATE,
  CONSTRAINT col_id PRIMARY KEY (col_id)
);

CREATE TABLE
  MNIS_VITAL_SIGN
(
  col_id                NUMBER,
  patient_id            VARCHAR2(256) NOT NULL,
  series                VARCHAR2(64)  NOT NULL,
  admission_id          VARCHAR2(256),
  dept_code             VARCHAR2(256) NOT NULL,
  ward_code             VARCHAR2(256) NOT NULL,
  bed_no                VARCHAR2(64),
  patient_name          VARCHAR2(256),
  sex_code              VARCHAR2(8),
  age_year              VARCHAR2(8),
  age_month             VARCHAR2(8),
  disease_diagnose_name VARCHAR2(512),
  in_hos_time           DATE,
  real_in_hos_time      NUMBER,
  after_operation_time  NUMBER,
  plan_time             DATE,
  record_time           DATE,
  breathe_frequency     VARCHAR2(64),
  pulse                 VARCHAR2(64),
  pacer_heart_rate      VARCHAR2(64),
  temperature           VARCHAR2(64),
  systolic_pressure     VARCHAR2(64),
  diastolic_pressure    VARCHAR2(64),
  weight                VARCHAR2(64),
  girth_of_paunch       VARCHAR2(64),
  is_ventilator         VARCHAR2(1),
  observe_name          VARCHAR2(512),
  observe_code          VARCHAR2(64),
  observe_val           VARCHAR2(64),
  remark                VARCHAR2(512),
  record_nurse_name     VARCHAR2(256),
  nurse_record_time     DATE,
  update_time           DATE,
  PRIMARY KEY (col_id)
)