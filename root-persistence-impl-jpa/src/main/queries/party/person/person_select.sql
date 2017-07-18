SELECT party.GLOBALIDENTIFIER_IDENTIFIER AS 'Global identifier',IFNULL(person.LASTNAMES,'') AS 'Lastnames'
,IFNULL(person.SURNAME,'') AS 'Surname',IFNULL(localitygid.CODE,'') AS 'Locality',IFNULL(sexgid.CODE,'') AS 'Sex'
,IFNULL(persontitlegid.CODE,'') AS 'Title',IFNULL(jobfunctiongid.CODE,'') AS 'Job function',IFNULL(jobtitlegid.CODE,'') AS 'Job title'
,file.IDENTIFIER AS 'Signature file name',file.EXTENSION AS 'Signature file extension'
FROM person

INNER JOIN party ON party.IDENTIFIER = person.IDENTIFIER
LEFT JOIN locality ON locality.IDENTIFIER = person.NATIONALITY_IDENTIFIER
LEFT JOIN globalidentifier AS localitygid ON localitygid.IDENTIFIER = locality.GLOBALIDENTIFIER_IDENTIFIER
LEFT JOIN sex ON sex.IDENTIFIER = person.SEX_IDENTIFIER
LEFT JOIN globalidentifier AS sexgid ON sexgid.IDENTIFIER = sex.GLOBALIDENTIFIER_IDENTIFIER

LEFT JOIN personextendedinformations ON personextendedinformations.PARTY_IDENTIFIER = party.IDENTIFIER
LEFT JOIN persontitle ON persontitle.IDENTIFIER = personextendedinformations.TITLE_IDENTIFIER
LEFT JOIN globalidentifier AS persontitlegid ON persontitlegid.IDENTIFIER = persontitle.GLOBALIDENTIFIER_IDENTIFIER

LEFT JOIN jobinformations ON jobinformations.PARTY_IDENTIFIER = party.IDENTIFIER
LEFT JOIN jobfunction ON jobfunction.IDENTIFIER = jobinformations.FUNCTION_IDENTIFIER
LEFT JOIN globalidentifier AS jobfunctiongid ON jobfunctiongid.IDENTIFIER = jobfunction.GLOBALIDENTIFIER_IDENTIFIER

LEFT JOIN jobtitle ON jobtitle.IDENTIFIER = jobinformations.TITLE_IDENTIFIER
LEFT JOIN globalidentifier AS jobtitlegid ON jobtitlegid.IDENTIFIER = jobtitle.GLOBALIDENTIFIER_IDENTIFIER

LEFT JOIN file ON file.IDENTIFIER = personextendedinformations.SIGNATURESPECIMEN_IDENTIFIER