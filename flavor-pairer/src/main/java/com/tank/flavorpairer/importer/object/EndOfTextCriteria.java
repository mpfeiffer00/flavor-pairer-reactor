package com.tank.flavorpairer.importer.object;

import javax.annotation.Nonnull;
import javax.annotation.processing.Generated;

import com.tank.flavorpairer.importer.PairingLevel;
import com.tank.flavorpairer.importer.util.DishAttribute;

/**
 * Criteria that contains information about the processing line.
 * 
 * @author tank
 *
 */
public class EndOfTextCriteria {
	/**
	 * Text of current line.
	 */
	private final String text;

	/**
	 * True if current text is a heading, false otherwise.
	 */
	private final boolean isHeading;

	/**
	 * True if the current text is a quote, false otherwise.
	 */
	private final boolean isQuote;

	/**
	 * True if the current text is an author, false otherwise.
	 */
	private final boolean isAuthor;

	/**
	 * The possibly null {@link DishAttribute} of the processing Header ingredient.
	 */
	private final DishAttribute dishAttribute;

	/**
	 * The possibly null {@link PairingLevel} of the processing ingredient.
	 */
	private final PairingLevel pairingLevel;

	@Generated("SparkTools")
	private EndOfTextCriteria(Builder builder) {
		this.text = builder.text;
		this.isHeading = builder.isHeading;
		this.isQuote = builder.isQuote;
		this.isAuthor = builder.isAuthor;
		this.dishAttribute = builder.dishAttribute;
		this.pairingLevel = builder.pairingLevel;
	}

	public String getText() {
		return text;
	}

	public boolean isHeading() {
		return isHeading;
	}

	public boolean isQuote() {
		return isQuote;
	}

	public boolean isAuthor() {
		return isAuthor;
	}

	public DishAttribute getDishAttribute() {
		return dishAttribute;
	}

	public PairingLevel getPairingLevel() {
		return pairingLevel;
	}

	/**
	 * Creates builder to build {@link EndOfTextCriteria}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link EndOfTextCriteria}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String text;
		private boolean isHeading;
		private boolean isQuote;
		private boolean isAuthor;
		private DishAttribute dishAttribute;
		private PairingLevel pairingLevel;

		private Builder() {
		}

		@Nonnull
		public Builder withText(@Nonnull String text) {
			this.text = text;
			return this;
		}

		@Nonnull
		public Builder withIsHeading(@Nonnull boolean isHeading) {
			this.isHeading = isHeading;
			return this;
		}

		@Nonnull
		public Builder withIsQuote(@Nonnull boolean isQuote) {
			this.isQuote = isQuote;
			return this;
		}

		@Nonnull
		public Builder withIsAuthor(@Nonnull boolean isAuthor) {
			this.isAuthor = isAuthor;
			return this;
		}

		@Nonnull
		public Builder withDishAttribute(@Nonnull DishAttribute dishAttribute) {
			this.dishAttribute = dishAttribute;
			return this;
		}

		@Nonnull
		public Builder withPairingLevel(@Nonnull PairingLevel pairingLevel) {
			this.pairingLevel = pairingLevel;
			return this;
		}

		@Nonnull
		public EndOfTextCriteria build() {
			return new EndOfTextCriteria(this);
		}
	}
}
