Para el hook:

cd .git
cd hooks
mv pre-commit.sample pre-commit
nano pre-commit

Se va a abrir una pestaña para editarlo
Abajo de lo comentado poner:

echo -e "About to run the formatter"
./gradlew verGJF

darle en ^x, despues save, despues enter.

Al hacer el commit se va a ejecutar el hook, y permitir hacer commit si el formatter corrió bien y no hay problemas.