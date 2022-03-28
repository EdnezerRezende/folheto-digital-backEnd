PIC TAGUATINGA

Aplicativo voltado para disponibilização de informações de devocionais, palavras e demais assuntos inerentes a Igreja de Cristo Taguatinga.

Configurações Necessárias:
    Variavéis de ambiente:
        (Capturar esses valores do Heroku)
        - S3_KEY=;
        - S3_SECRET=;
        - SENHA_EMAIL=;
        - DEBUG_EMAIL=false

    Ter banco Mysql disponível na máquina;
    Alterar, no application.properties, a propriedade spring.profiles.active para dev (irá acessar os valores locais);
