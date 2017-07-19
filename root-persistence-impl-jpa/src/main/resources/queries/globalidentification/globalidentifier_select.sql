SELECT globalidentifier.IDENTIFIER AS 'Identifier',IFNULL(globalidentifier.CODE,'') AS 'Code',IFNULL(globalidentifier.NAME,'') AS 'Name'
,IFNULL(globalidentifier.DESCRIPTION,'') AS 'Description',IFNULL(globalidentifier.ABBREVIATION,'') AS 'Abbreviation'
,IFNULL(globalidentifier.OTHERDETAILS,'') AS 'Other details',IFNULL(globalidentifier.EXTERNALIDENTIFIER,'') AS 'External identifier'
,IFNULL(globalidentifier.ORDERNUMBER,'') AS 'Order number',IFNULL(globalidentifier.WEIGHT,'') AS 'Weight'
,IFNULL(globalidentifier.USABLE,'') AS 'Usable',IFNULL(globalidentifier.FROMDATE,'') AS 'Birth date'
,IFNULL(globalidentifier.TODATE,'') AS 'Death date'
FROM globalidentifier
