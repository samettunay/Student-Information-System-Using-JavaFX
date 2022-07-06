-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 03 Haz 2022, 17:46:19
-- Sunucu sürümü: 10.4.22-MariaDB
-- PHP Sürümü: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `istedb`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `academician`
--

CREATE TABLE `academician` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `lastname` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `gender` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `mail` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `academician`
--

INSERT INTO `academician` (`id`, `username`, `password`, `name`, `lastname`, `gender`, `mail`) VALUES
(1, 'akademisyen', '81dc9bdb52d04dc20036dbd8313ed055', 'Samet', 'Tunay', 'Erkek', 'samet.tunay@iste.edu.tr');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `examschedule`
--

CREATE TABLE `examschedule` (
  `id` int(11) NOT NULL,
  `gradeYear` int(11) NOT NULL,
  `Ders` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `Derslik` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `Saat` time NOT NULL,
  `Bölüm` varchar(50) COLLATE utf8_turkish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `examschedule`
--

INSERT INTO `examschedule` (`id`, `gradeYear`, `Ders`, `Derslik`, `Saat`, `Bölüm`) VALUES
(1, 3, 'Mikroişlemciler', 'A-207', '14:20:00', 'Bilgisayar Mühendisliği'),
(2, 3, 'Nesne Tabanlı Programlama', 'A-205', '13:00:00', 'Bilgisayar Mühendisliği'),
(3, 3, 'Bilgisayar Ağları', 'A-205', '10:30:00', 'Bilgisayar Mühendisliği'),
(4, 3, 'Web Tabanlı Programlama', 'A-204', '08:30:00', 'Bilgisayar Mühendisliği'),
(5, 3, 'Yazılım Mühendisliğine Giriş', 'A-201', '12:00:00', 'Bilgisayar Mühendisliği');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `lessons`
--

CREATE TABLE `lessons` (
  `id` int(11) NOT NULL,
  `studentNumber` int(11) NOT NULL,
  `department` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `Ders` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `gradeYear` int(11) NOT NULL,
  `Vize` double NOT NULL DEFAULT 0,
  `Final` double NOT NULL DEFAULT 0,
  `Ortalama` double NOT NULL DEFAULT 0,
  `Devamsizlik` int(11) NOT NULL DEFAULT 0,
  `Durum` varchar(50) COLLATE utf8_turkish_ci NOT NULL DEFAULT 'Giriş Yapılmamış',
  `Derslik` varchar(50) COLLATE utf8_turkish_ci NOT NULL DEFAULT 'A-201',
  `Saat` time NOT NULL DEFAULT '10:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `lessons`
--

INSERT INTO `lessons` (`id`, `studentNumber`, `department`, `Ders`, `gradeYear`, `Vize`, `Final`, `Ortalama`, `Devamsizlik`, `Durum`, `Derslik`, `Saat`) VALUES
(13, 192523068, 'Bilgisayar Mühendisliği', 'Nesne Tabanlı Programlama', 3, 50, 50, 50, 4, 'Kaldı', 'A-207', '17:00:00'),
(14, 192523068, 'Bilgisayar Mühendisliği', 'Staj', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-205', '17:00:00'),
(15, 192523068, 'Bilgisayar Mühendisliği', 'Mikroişlemciler', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-206', '17:00:00'),
(16, 192523068, 'Bilgisayar Mühendisliği', 'Bilgisayar Ağları', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-203', '17:00:00'),
(17, 192523068, 'Bilgisayar Mühendisliği', 'Web Tabanlı Programlama', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-201', '17:00:00'),
(18, 192523068, 'Bilgisayar Mühendisliği', 'Yazılım Mühendisliğine Giriş', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-202', '17:00:00'),
(19, 192523068, 'Bilgisayar Mühendisliği', 'C# ile Görsel Programlama', 2, 50, 40, 44, 2, 'Geçti', 'A-209', '17:00:00'),
(20, 192523068, 'Bilgisayar Mühendisliği', 'İşletim Sistemleri', 2, 50, 40, 44, 2, 'Geçti', 'A-205', '10:30:00'),
(21, 192523068, 'Bilgisayar Mühendisliği', 'Olasılık ve İstatistik', 2, 100, 100, 100, 1, 'Geçti', 'A-207', '14:20:00'),
(22, 192523068, 'Bilgisayar Mühendisliği', 'Lineer Cebir', 2, 100, 100, 100, 1, 'Geçti', 'A-201', '10:30:00'),
(23, 192523068, 'Bilgisayar Mühendisliği', 'Sayısal Tasarım II', 2, 50, 50, 50, 3, 'Geçti', 'A-205', '10:30:00'),
(24, 192523068, 'Bilgisayar Mühendisliği', 'Sayısal Tasarım Laboratuvarı', 2, 40, 50, 46, 1, 'Geçti', 'A-205', '17:00:00'),
(37, 192523067, 'Bilgisayar Mühendisliği', 'Nesne Tabanlı Programlama', 3, 20, 60, 44, 0, 'Geçti', 'A-201', '10:00:00'),
(38, 192523067, 'Bilgisayar Mühendisliği', 'Staj', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-201', '10:00:00'),
(39, 192523067, 'Bilgisayar Mühendisliği', 'Mikroişlemciler', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-201', '10:00:00'),
(40, 192523067, 'Bilgisayar Mühendisliği', 'Bilgisayar Ağları', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-201', '10:00:00'),
(41, 192523067, 'Bilgisayar Mühendisliği', 'Web Tabanlı Programlama', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-201', '10:00:00'),
(42, 192523067, 'Bilgisayar Mühendisliği', 'Yazılım Mühendisliğine Giriş', 3, 0, 0, 0, 0, 'Giriş Yapılmamış', 'A-201', '10:00:00');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `number` int(9) NOT NULL,
  `name` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `lastname` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `gender` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `department` varchar(50) COLLATE utf8_turkish_ci NOT NULL,
  `gradeYear` int(11) NOT NULL,
  `formalEducation` int(1) NOT NULL DEFAULT 1,
  `average` double NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_turkish_ci;

--
-- Tablo döküm verisi `students`
--

INSERT INTO `students` (`id`, `number`, `name`, `lastname`, `gender`, `password`, `department`, `gradeYear`, `formalEducation`, `average`) VALUES
(8, 192523068, 'Hacı Samet', 'TUNAY', 'Erkek', '81dc9bdb52d04dc20036dbd8313ed055', 'Bilgisayar Mühendisliği', 3, 1, 0),
(19, 192523067, 'Ayşe', 'Fatma', 'Kadın', '81dc9bdb52d04dc20036dbd8313ed055', 'Bilgisayar Mühendisliği', 3, 1, 0);

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `academician`
--
ALTER TABLE `academician`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `examschedule`
--
ALTER TABLE `examschedule`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `lessons`
--
ALTER TABLE `lessons`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `number` (`number`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `academician`
--
ALTER TABLE `academician`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Tablo için AUTO_INCREMENT değeri `examschedule`
--
ALTER TABLE `examschedule`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Tablo için AUTO_INCREMENT değeri `lessons`
--
ALTER TABLE `lessons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- Tablo için AUTO_INCREMENT değeri `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
