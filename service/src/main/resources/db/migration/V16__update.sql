ALTER TABLE `psn`.`game`
  DROP INDEX `name_desc_full_index`,
  ADD FULLTEXT INDEX `name_zhName_full_index`(`name`, `zhName`) WITH PARSER `ngram`;