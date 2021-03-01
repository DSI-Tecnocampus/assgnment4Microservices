DROP TABLE IF EXISTS `productService`;
CREATE TABLE `productService` (
    `id` varchar(256) NOT NULL,
    `name` varchar(45) NOT NULL,
    `description` varchar(255) NOT NULL,
    `weight` int(11) NOT NULL,
    PRIMARY KEY (`id`)
);
