rename table users_records to record_logs;
alter table record_logs add column reps int;
alter table record_logs add column time int;
alter table record_logs add column distance double;