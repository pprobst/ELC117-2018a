# Editor de Grafos em JavaFX
##### Para gerar o .jar e executar o programa, digite os seguintes comandos no diretório `t5`:

```bash
javac -d classes src/*.java
javapackager -createjar -srcdir classes -outfile editor.jar -appclass t5.Tela
java -jar editor.jar
```

Observação: arestas descontínuas (pontilhadas) não são contadas como
sobrepostas. Nestes casos, é como se ela estivesse "acima" ou "abaixo" da outra
aresta.
