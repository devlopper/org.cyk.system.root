DROP PROCEDURE IF EXISTS dump_file_bytes;
delimiter //
	CREATE PROCEDURE dump_file_bytes(IN file_directory VARCHAR(255),IN mime_filter VARCHAR(255)) begin
		DECLARE v_identifier int;
		DECLARE v_extension varchar(255) DEFAULT "";
		DECLARE v_cursor CURSOR FOR SELECT identifier,extension FROM file WHERE mime LIKE mime_filter;
		OPEN v_cursor;
		read_loop: LOOP
     		FETCH v_cursor INTO v_identifier,v_extension;
     		SET @QUERY = CONCAT('SELECT bytes FROM file WHERE identifier=', v_identifier,' INTO DUMPFILE "',file_directory,'/',v_identifier,'.',v_extension,'"');
     		PREPARE write_file FROM @QUERY;
     		EXECUTE write_file;
   	END LOOP;
 		CLOSE v_cursor;
	END //
delimiter ;