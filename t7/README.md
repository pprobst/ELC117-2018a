# Dashboard para monitoramento de frota de ônibus urbanos (RJ) em JavaFX
##### Para gerar o .jar e executar o programa, digite os seguintes comandos no diretório `t7`:

```bash
mkdir src/classes && javac -d src/classes/ src/java/*.java
javapackager -createjar -srcdir src/classes -outfile FrotaRJ.jar -appclass t7.Interface
java -jar FrotaRJ.jar 
```
