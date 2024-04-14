# app-marvel-android
App Android Nativo que consulta a API da Marvel: https://developer.marvel.com/ <br>

O App roda a partir do Android 8.0 (API Level 26), usa a arquitetura MVVM, Injeção de Dependências, Persistência dos dados off-line, Teste de Integração e Teste de UI.

<hr>

Dependências:
-------------
O App usa as seguintes dependências:

* Constraint Layout
* Coroutines
* Coroutines Test
* Espresso
* Fragment Testing
* Glide
* Koin
* jUnit
* Life Cycle
* Live Data
* Material Design
* MokK
* Navigation
* Ok HTTP
* Recycler View Matcher
* Retrofit
* Room
* View Model

Configuração:
-------------
Colocar as seguintes propiedades no arquivo `local.properties`: <br>
* `MARVEL_BASE_URL` - Com a base do endpoint da API
* `MARVEL_API_KEY` - Com a KEY da API
* `MARVEL_MD5_HASH` - Com o Hash da API

<br>

Criar o arquivo `secrets.properties` no diretório raiz do projeto e colocar a propriedade `MAPS_API_KEY` com a API_KEY do Google Maps. <br>

Exmpelo:<br>
`MAPS_API_KEY=<API_KEY_DO_GOOGLE_MAPS>`

<hr>


