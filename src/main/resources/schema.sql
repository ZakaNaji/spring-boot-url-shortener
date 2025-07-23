CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE short_url
(
    id bigserial primary key,
    short_key varchar(100) not null unique,
    original_url text not null,
    is_private boolean not null default false,
    created_at timestamp not null default current_timestamp,
    expires_at timestamp,
    created_by bigint,
    click_count bigint not null default 0,
    constraint fk_short_url_users foreign key (created_by) references users(id)
)