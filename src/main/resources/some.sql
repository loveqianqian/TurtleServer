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

