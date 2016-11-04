@echo off
REM write config to zookeeper

setlocal
REM set config home 
set CONFIG_HOME=%~dp0%..
set CLASSPATH=%CONFIG_HOME%\lib\*;%CONFIG_HOME%\conf;%CONFIG_HOME%\bin;%CONFIG_HOME%;%CONFIG_HOME%\tmp
set WRITE_MAIN=com.tydic.config.Write
set READ_MAIN=com.tydic.config.Read
set LIST_MAIN=com.tydic.config.Ls
set CAT_MAIN=com.tydic.config.Cat
set DELETE_MAIN=com.tydic.config.Delete
set COPY_MAIN=com.tydic.config.Copy

@echo on
@if %1==ls (
	goto ls
) else (
	if %1==cat (
		goto cat
	) else (
		if %1==write (
			goto write
		) else (
			if %1==read (
				goto read
			) else (
				if %1==delete (
					goto delete
				) else (
					if %1==copy (
						goto copy
					) else (
						goto error
					)
				)
			)
		)
	)
	goto 
)
:ls
java -cp "%CLASSPATH%" %LIST_MAIN% %2 %3 %4 %5 %6 %7 %8
@goto end

:cat
java -cp "%CLASSPATH%" %CAT_MAIN% %2 %3 %4 %5 %6 %7 %8
@goto end

:write
java -cp "%CLASSPATH%" %WRITE_MAIN% %2 %3 %4 %5 %6 %7 %8
@goto end

:read
java -cp "%CLASSPATH%" %READ_MAIN% %2 %3 %4 %5 %6 %7 %8
@goto end

:delete
java -cp "%CLASSPATH%" %DELETE_MAIN% %2 %3 %4 %5 %6 %7 %8
@goto end

:copy
Rem java -cp "%CLASSPATH%" %COPY_MAIN% %2 %~dp3 %4 %5 %6 %7 %8
@goto end

:error
@echo "Usage: %0 {ls [-p path]|cat|write|read|delete|copy}"
@goto end

:end
@endlocal
@pause


