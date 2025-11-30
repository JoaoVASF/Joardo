INSERT INTO usuario (nome, email, senha, telefone, e_admin) 
VALUES ('Administrador', 'admin@admin.com', 'admin', '99999-9999', true)
ON CONFLICT (email) DO NOTHING;