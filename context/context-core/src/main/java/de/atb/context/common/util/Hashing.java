package de.atb.context.common.util;

/*
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */

import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Hashing
 *
 * @author scholze
 * @version $LastChangedRevision: 417 $
 *
 */
public class Hashing {

	public static final String ALGORITHM_MD5 = "MD5";

	public static final String ALGORITHM_SHA1 = "SHA-1";

	public static final String ALGORITHM_SHA256 = "SHA-256";

	private Hashing(){}

	/**
	 * Calculates the MD5 hash sum of the given string.
	 *
	 * @param toHash
	 *            the string to get the MD5 hash for.
	 * @return the MD5 hash for the specified string.
	 */
	public static String getMD5Hash(final String toHash) {
		return Hashing.getStringHash(toHash, Hashing.ALGORITHM_MD5);
	}

	/**
	 * Calculates the SHA-1 hash sum of the given string.
	 *
	 * @param toHash
	 *            the string to get the SHA-1 hash for.
	 * @return the SHA-1 hash for the specified string.
	 */
	public static String getSHA1Hash(final String toHash) {
		return Hashing.getStringHash(toHash, Hashing.ALGORITHM_SHA1);
	}

	/**
	 * Calculates the SHA-256 hash sum of the given string.
	 *
	 * @param toHash
	 *            the string to get the SHA-256 hash for.
	 * @return the SHA-256 hash for the specified string.
	 */
	public static String getSHA256Hash(final String toHash) {
		return Hashing.getStringHash(toHash, Hashing.ALGORITHM_SHA1);
	}

	/**
	 * Calculates the hash for the given string by using the given algorithm.
	 *
	 * @param toHash
	 *            the string to get the hash for.
	 * @param algorithm
	 *            the message digest algorithm used for hashing (like &quot;
	 *            {@code MD5}&quot; or &quot;<code>SHA-1</code>&quot;).
	 * @return the hash for the specified string.
	 */
	public static String getStringHash(final String toHash,
			final String algorithm) {
		try {
			final MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(toHash.getBytes());
			return Hashing.formatBytesArray(md.digest());
		} catch (final NoSuchAlgorithmException e) {
			LoggerFactory.getLogger(Hashing.class).error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Calculates the MD5 check sum of the given file.
	 *
	 * @param fileName
	 *            the name (and path) of the file to calculate the MD5 checksum
	 *            for.
	 * @return the MD5 based checksum of the given file.
	 */
	public static String getMD5Checksum(final String fileName) {
		return Hashing.getChecksum(new File(fileName), Hashing.ALGORITHM_MD5);
	}

	/**
	 * Calculates the SHA-1 check sum of the given file.
	 *
	 * @param fileName
	 *            the name (and path) of the file to calculate the SHA-1
	 *            checksum for.
	 * @return the SHA-1 based checksum of the given file.
	 */
	public static String getSHA1Checksum(final String fileName) {
		return Hashing.getChecksum(new File(fileName), Hashing.ALGORITHM_SHA1);
	}

	/**
	 * Calculates the SHA-1 check sum of the given file.
	 *
	 * @param fileName
	 *            the name (and path) of the file to calculate the SHA-1
	 *            checksum for.
	 * @return the SHA-1 based checksum of the given file.
	 */
	public static String getSHA256Checksum(final String fileName) {
		return Hashing
				.getChecksum(new File(fileName), Hashing.ALGORITHM_SHA256);
	}

	/**
	 * Calculates the MD5 check sum of the given file.
	 *
	 * @param file
	 *            the file to calculate the MD5 checksum for.
	 * @return the MD5 based checksum of the given file.
	 */
	public static String getMD5Checksum(final File file) {
		return Hashing.getChecksum(file, Hashing.ALGORITHM_MD5);
	}

	/**
	 * Calculates the SHA-1 check sum of the given file.
	 *
	 * @param file
	 *            the file to calculate the SHA-1 checksum for.
	 * @return the SHA-1 based checksum of the given file.
	 */
	public static String getSHA1Checksum(final File file) {
		return Hashing.getChecksum(file, Hashing.ALGORITHM_SHA1);
	}

	/**
	 * Calculates the SHA-1 check sum of the given file.
	 *
	 * @param file
	 *            the file to calculate the SHA-1 checksum for.
	 * @return the SHA-1 based checksum of the given file.
	 */
	public static String getSHA256Checksum(final File file) {
		return Hashing.getChecksum(file, Hashing.ALGORITHM_SHA256);
	}

	/**
	 * Calculates the check sum of the given file by using the given algorithm.
	 *
	 * @param fileName
	 *            the name (and path) of the file to calculate the MD5 checksum
	 *            for.
	 * @param algorithm
	 *            the message digest algorithm used for hashing (like &quot;
	 *            {@code MD5}&quot; or &quot;<code>SHA-1</code>&quot;).
	 * @return the checksum of the given file.
	 */
	public static String getChecksum(final String fileName,
			final String algorithm) {
		return Hashing.getChecksum(new File(fileName), algorithm);
	}

	/**
	 * Calculates the check sum of the given file by using the given algorithm.
	 *
	 * @param file
	 *            the file to calculate the MD5 checksum for.
	 * @param algorithm
	 *            the message digest algorithm used for hashing (like &quot;
	 *            {@code MD5}&quot; or &quot;<code>SHA-1</code>&quot;).
	 * @return the checksum of the given file.
	 */
	public static String getChecksum(final File file, final String algorithm) {
		try {
			final byte[] bytes = Hashing.getFileHash(file, algorithm);
			return Hashing.formatBytesArray(bytes);
		} catch (final Exception e) {
			LoggerFactory.getLogger(Hashing.class).error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Formats the given byte array as a hexadecimal string.
	 *
	 * @param bytes
	 *            the byte array to be converted to a hexadecimal string.
	 * @return the byte array as a hexadecimal string (not containing 0x at the
	 *         beginning).
	 */
	protected static String formatBytesArray(final byte[] bytes) {
		final StringBuffer result = new StringBuffer(32);
		final Formatter f = new Formatter(result);
		for (final byte b : bytes) {
			f.format("%02x", b);
		}
		f.close();
		return result.toString();
	}

	/**
	 * Calculates the hash value for the given file, using the given digesting
	 * algorithm.
	 *
	 * @param file
	 *            the file to calulcate the hash of.
	 * @param algorithm
	 *            the message digest algorithm used for hashing (like &quot;
	 *            {@code MD5}&quot; or &quot;<code>SHA-1</code>&quot;).
	 * @return the array of bytes for the resulting hash value.
	 * @throws NoSuchAlgorithmException
	 *             if no Provider supports a MessageDigestSpi implementation for
	 *             the specified algorithm.
	 * @throws IOException
	 *             If the first byte cannot be read for any reason other than
	 *             the end of the file, if the input stream has been closed, or
	 *             if some other I/O error occurs.
	 */
	protected static byte[] getFileHash(final File file, final String algorithm)
			throws NoSuchAlgorithmException, IOException {
		final byte[] buffer = new byte[1024];
		final MessageDigest complete = MessageDigest.getInstance(algorithm);
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);
			int numRead;
			do {
				numRead = fis.read(buffer);
				if (numRead > 0) {
					complete.update(buffer, 0, numRead);
				}
			} while (numRead != -1);
			fis.close();
		} catch (final IOException io) {
			if (fis != null) {
				fis.close();
			}
			throw io;
		}
		return complete.digest();
	}
}
