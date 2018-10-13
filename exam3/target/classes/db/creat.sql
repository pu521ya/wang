DROP TABLE IF EXISTS  dif_Information;

CREATE TABLE IF NOT EXISTS dif_Information (
  id int  NOT NULL AUTO_INCREMENT NOT NULL COMMENT '自增主键',
source_content varchar(1000) NOT NULL COMMENT '源文件内容',
target_content varchar(1000) NOT NULL COMMENT '目标文件内容',
difference VARCHAR(2000) NOT NULL COMMENT '差异',
time TIMESTAMP NOT NULL  COMMENT '对比时间',
PRIMARY KEY (id)
)COMMENT='文件对比差异表';

insert into dif_Information(source_content,target_content,difference,time)
VALUES ('a=x','b=y','-a=x;+b=y','2017-08-17 09:36:02.853');
insert into dif_Information(source_content,target_content,difference,time)
VALUES ('a=x','a=y','-a=x;+a=y','2017-08-18 09:36:02.853');
insert into dif_Information(source_content,target_content,difference,time)
VALUES ('a=x','b=y','-a=x;+b=y','2017-08-19 09:36:02.853');
insert into dif_Information(source_content,target_content,difference,time)
VALUES ('a=x','a=y','-a=x;+a=y','2017-08-20 09:36:02.853');
insert into dif_Information(source_content,target_content,difference,time)
VALUES ('a=x','b=y','-a=x;+b=y','2017-08-21 09:36:02.853');
insert into dif_Information(source_content,target_content,difference,time)
VALUES ('a=x','a=y','-a=x;+a=y','2017-08-22 09:36:02.853');