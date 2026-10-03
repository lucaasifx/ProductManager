-- V2: Adiciona coluna image_url e faz carga inicial de Cafés Especiais e Equipamentos Barista

-- Adiciona coluna de imagem na tabela product
ALTER TABLE product ADD COLUMN IF NOT EXISTS image_url VARCHAR(500);

-- Inserção de Categorias
INSERT INTO category (name, description, active, created_at) VALUES
('Grãos Especiais', 'Cafés especiais 100% arábica com pontuação acima de 84 pontos SCA e torra fresca.', TRUE, CURRENT_TIMESTAMP),
('Métodos de Extração', 'Cafeteiras manuais e suportes de filtragem para extração artesanal.', TRUE, CURRENT_TIMESTAMP),
('Moedores de Precisão', 'Moedores manuais e elétricos com mós cônicas em aço inox.', TRUE, CURRENT_TIMESTAMP),
('Acessórios Barista', 'Balanças com timer, chaleiras bico de ganso e termômetros de precisão.', TRUE, CURRENT_TIMESTAMP);

-- Inserção de Produtos com Imagens Reais e Específicas
INSERT INTO product (id, name, description, price, stock_quantity, category_id, image_url, created_at) VALUES
(gen_random_uuid(), 'Bourbon Amarelo - Serra da Mantiqueira 250g', 'Notas de caramelo, chocolate ao leite e acidez cítrica média. 86 pontos SCA.', 44.90, 35, (SELECT id FROM category WHERE name = 'Grãos Especiais'), 'https://images.unsplash.com/photo-1647551270770-b8ccdab49519?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Geisha Especial - Sul de Minas 250g', 'Flor de laranjeira, pêssego e jasmim. Corpo sedoso e finalização prolongada. 89 pontos SCA.', 89.00, 18, (SELECT id FROM category WHERE name = 'Grãos Especiais'), 'https://images.unsplash.com/photo-1690983327218-4e326ec9e5a4?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Catuaí Vermelho Fermentado 250g', 'Processo natural anaeróbico com notas de frutas vermelhas e vinho licoroso. 87 pontos SCA.', 52.50, 24, (SELECT id FROM category WHERE name = 'Grãos Especiais'), 'https://images.unsplash.com/photo-1767020364648-d113fec4ee63?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Mogiana Paulista Microlote 250g', 'Notas de avelã, rapadura e chocolate meio amargo. Doçura alta e final limpo. 85 pontos SCA.', 39.90, 40, (SELECT id FROM category WHERE name = 'Grãos Especiais'), 'https://images.unsplash.com/photo-1684420742076-a9641a1f2428?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Cafeteira Prensa Francesa 600ml Inox', 'Vidro borossilicato de alta resistência térmica e filtro duplo em aço inox 304.', 129.90, 15, (SELECT id FROM category WHERE name = 'Métodos de Extração'), 'https://images.unsplash.com/photo-1708127368781-cd5f069a90a5?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Dripper V60 Acrílico Transparente 02', 'Suporte cônico com espirais internas projetadas para fluxo perfeito de extração.', 68.00, 45, (SELECT id FROM category WHERE name = 'Métodos de Extração'), 'https://images.unsplash.com/photo-1771508706219-9781c79787e9?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Aeropress Original Coffee Maker', 'Sistema de imersão e pressão manual que proporciona café encorpado e sem amargor.', 289.00, 12, (SELECT id FROM category WHERE name = 'Métodos de Extração'), 'https://images.unsplash.com/photo-1771519705496-cddb76845fd0?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Cafeteira Italiana Moka 6 Xícaras', 'Corpo clássico em alumínio fundido para espresso encorpado em fogão tradicional.', 119.00, 20, (SELECT id FROM category WHERE name = 'Métodos de Extração'), 'https://images.unsplash.com/photo-1748010445321-6255efc521d2?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Moedor Manual Timemore Chestnut C2', 'Mós cônicas de aço inoxidável 38mm com regulagem ponto a ponto de moagem.', 349.90, 10, (SELECT id FROM category WHERE name = 'Moedores de Precisão'), 'https://images.unsplash.com/photo-1774801935390-c0f7dbe14175?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Moedor Elétrico de Café Mazzer Pro', 'Mós planas de 64mm, dosagem sob demanda e precisão micrométrica para espresso e coados.', 890.00, 8, (SELECT id FROM category WHERE name = 'Moedores de Precisão'), 'https://images.unsplash.com/photo-1789009547716-bd710193c25d?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Chaleira Bico de Ganso com Termômetro 1L', 'Aço inoxidável com bico pescoço de cisne para controle milimétrico de vazão.', 179.90, 16, (SELECT id FROM category WHERE name = 'Acessórios Barista'), 'https://images.unsplash.com/photo-1768674150917-55b1ddf46a02?w=800', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Balança Digital Barista com Timer 0.1g', 'Display LED touch invisível, bateria recarregável USB-C e precisão de 0.1g.', 149.00, 30, (SELECT id FROM category WHERE name = 'Acessórios Barista'), 'https://upload.wikimedia.org/wikipedia/commons/3/32/Brewing_pour_over_coffee_with_kettle_and_scale.jpg', CURRENT_TIMESTAMP);
