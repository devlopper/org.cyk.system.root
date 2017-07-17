SELECT partygid.CODE AS 'User',credentials.USERNAME AS 'Username',credentials.PASSWORD AS 'Password'
FROM useraccount

INNER JOIN party ON party.IDENTIFIER = useraccount.USER_IDENTIFIER
INNER JOIN globalidentifier AS partygid ON partygid.IDENTIFIER = party.GLOBALIDENTIFIER_IDENTIFIER
INNER JOIN person ON person.IDENTIFIER = party.IDENTIFIER

INNER JOIN credentials ON credentials.IDENTIFIER = useraccount.CREDENTIALS_IDENTIFIER
