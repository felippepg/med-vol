ALTER TABLE consultas ADD COLUMN motivo_cancelamento VARCHAR(100);
UPDATE consultas SET motivo_cancelamento = NULL;