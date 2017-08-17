Como gerar o arquivo ormlite-config.txt
=======================================

O arquivo `ormlite-config.txt` faz com que a inicialização dos DAOs do ormlite ocorra
 de maneira eficiente. Veja a explicação
 no [site do ormlite](http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_4.html#Config-Optimization).

Este arquivo deve ser gerado **manualmente** sempre que houver uma alteração em alguma
 classe persistida pelo ormlite.

1. No Android Studio, crie uma nova configuração de execução em `Run` > `Edit Configurations...`
2. Crie uma nova configuração com o seguinte:
    * Main class: `br.com.bg7.appvistoria.data.source.local.ormlite.OrmLiteConfigGenerator`
    * Working directory: `$MODULE_DIR$/src/main`
    * JRE: JRE do Java normal, não o JRE do Android
3. Salve e execute sua nova configuração
4. Você deve ver uma saída semelhante ao seguinte:

> `Writing configurations to /code/vistoria/app/src/main/./res/raw/ormlite-config.txt`
>
> `Wrote config for class br.com.bg7.appvistoria.data.Config`
>
> `Wrote config for class br.com.bg7.appvistoria.data.Inspection`
>
> `Wrote config for class br.com.bg7.appvistoria.data.Picture`
>
> `Wrote config for class br.com.bg7.appvistoria.data.User`
>
> `Done.`
