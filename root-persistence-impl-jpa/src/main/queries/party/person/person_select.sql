SELECT party.GLOBALIDENTIFIER_IDENTIFIER AS 'Global identifier',IFNULL(person.LASTNAMES,'') AS 'Lastnames'
,IFNULL(person.SURNAME,'') AS 'Surname',IFNULL(localitygid.CODE,'') AS 'Locality',IFNULL(sexgid.CODE,'') AS 'Sex'
FROM person

INNER JOIN party ON party.IDENTIFIER = person.IDENTIFIER
LEFT JOIN locality ON locality.IDENTIFIER = person.NATIONALITY_IDENTIFIER
LEFT JOIN globalidentifier AS localitygid ON localitygid.IDENTIFIER = locality.GLOBALIDENTIFIER_IDENTIFIER
LEFT JOIN sex ON sex.IDENTIFIER = person.SEX_IDENTIFIER
LEFT JOIN globalidentifier AS sexgid ON sexgid.IDENTIFIER = sex.GLOBALIDENTIFIER_IDENTIFIER