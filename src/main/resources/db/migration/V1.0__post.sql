
create table if not exists `result`(
`name`        varchar(36) PRIMARY KEY,
`address`      varchar(500),
`city`        varchar(36),
`country`        varchar(36),
`result_status` varchar(36),
`pincode`        INT,
`sat_score`        INT,
`created_ts` DATETIME,
`updated_ts` DATETIME
);
