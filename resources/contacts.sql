-- :name create-contacts-table
-- :command :execute
-- :result :raw
-- :doc creates contacts table
create table contacts (
  id serial primary key,
  first_name text,
  last_name text,
  email text,
  created_at timestamp not null default current_timestamp
);