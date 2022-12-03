create table public.bank_accounts
(
    id                 bigserial
        primary key
        unique,
    from_currency_code text,
    to_currency_code   text,
    exchange_rate      text
);


create table accounts
(
    id             bigserial
        primary key
        unique,
    account_number text,
    balance        float not null,
    created_date   date,
    modified_date  date,
    limit_usd      float not null
);