declare
 myresult sys_refcursor;
 tbl_id QUOTES.Q_ID%TYPE;
 tbl_quote QUOTES.QUOTE%TYPE;
 tbl_author QUOTES.AUTHOR%TYPE;
begin
 find_quotes(myresult);
 loop
   fetch myresult
   into tbl_id, tbl_quote, tbl_author;
   exit when myresult%NOTFOUND;
   DBMS_OUTPUT.PUT_LINE(tbl_id || ' | ' || tbl_quote || ' | ' || tbl_author);
 end loop;
 close myresult;
end;