# Planaridade em JavaFX
##### Para gerar o .jar e executar o programa, digite os seguintes comandos no diretório `t6`:

```bash
mkdir src/classes && javac -d src/classes/ src/java/*.java
mv src/resources/*.png src/classes/t6/
javapackager -createjar -srcdir src/classes -outfile planaridade.jar -appclass t6.Tela
java -jar planaridade.jar
```
