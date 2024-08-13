create table Schedule(
    scheduleId bigint auto_increment primary key,
    name VARCHAR(10) not null,
    password VARCHAR(100) not null,
    creationDate timestamp default current_timestamp not null, # 입력하면 자동으로 현재 입력 시간이 들어감
    modifyDate timestamp default current_timestamp on update current_timestamp not null, # 수정될 때마다 자동 업데이트
    scheduleInformation VARCHAR(100) not null
);