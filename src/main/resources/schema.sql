CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100),
    telefone VARCHAR(20),
    e_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    descricao TEXT,
    imagem VARCHAR(255),
    preco DECIMAL(10, 2),
    quantidade_estoque INTEGER,
    ativo BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS item_carrinho (
    id SERIAL PRIMARY KEY,
    id_usuario INTEGER REFERENCES usuario(id),
    id_produto INTEGER REFERENCES produto(id) ON DELETE CASCADE,
    quantidade INTEGER
);