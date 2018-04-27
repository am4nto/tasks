call runcrud
if "%ERRORLEVEL%" == "0" goto runbrowser
echo.
echo runcrud error
goto fail

:runbrowser
call "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" -url http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.