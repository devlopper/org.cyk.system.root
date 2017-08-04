SELECT IFNULL(partygid.CODE,'') AS 'Person' , IFNULL(phonenumber.NUMBER,'') AS 'Number'
FROM phonenumber

INNER JOIN contact ON contact.IDENTIFIER = phonenumber.IDENTIFIER
INNER JOIN contactcollection ON contactcollection.IDENTIFIER = contact.COLLECTION_IDENTIFIER
INNER JOIN party ON party.CONTACTCOLLECTION_IDENTIFIER = contactcollection.IDENTIFIER
INNER JOIN globalidentifier AS partygid ON partygid.IDENTIFIER = party.GLOBALIDENTIFIER_IDENTIFIER
INNER JOIN person ON person.IDENTIFIER = party.IDENTIFIER