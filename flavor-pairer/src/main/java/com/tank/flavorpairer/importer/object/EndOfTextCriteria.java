package com.tank.flavorpairer.importer.object;

import javax.annotation.Nonnull;
import javax.annotation.processing.Generated;

import com.tank.flavorpairer.importer.PairingLevel;
import com.tank.flavorpairer.importer.util.DishAttribute;

public class EndOfTextCriteria {
	private final String text;
	private final boolean isHeading;
	private final boolean isFlavorAffinityEntries;
	private final boolean isQuote;
	private final boolean isAuthor;
	private final DishAttribute dishAttribute;
	private final PairingLevel pairingLevel;
	private final boolean isEndingWithColon;

	@Generated("SparkTools")
	private EndOfTextCriteria(Builder builder) {
		this.text = builder.text;
		this.isHeading = builder.isHeading;
		this.isFlavorAffinityEntries = builder.isFlavorAffinityEntries;
		this.isQuote = builder.isQuote;
		this.isAuthor = builder.isAuthor;
		this.dishAttribute = builder.dishAttribute;
		this.pairingLevel = builder.pairingLevel;
		this.isEndingWithColon = builder.isEndingWithColon;
	}

	public String getText() {
		return text;
	}

	public boolean isHeading() {
		return isHeading;
	}

	public boolean isFlavorAffinityEntries() {
		return isFlavorAffinityEntries;
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

	public boolean isEndingWithColon() {
		return isEndingWithColon;
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
		private boolean isFlavorAffinityEntries;
		private boolean isQuote;
		private boolean isAuthor;
		private DishAttribute dishAttribute;
		private PairingLevel pairingLevel;
		private boolean isEndingWithColon;

		private Builder() {
		}

		/**
		 * Builder method for text parameter.
		 * 
		 * @param text field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withText(@Nonnull String text) {
			this.text = text;
			return this;
		}

		/**
		 * Builder method for isHeading parameter.
		 * 
		 * @param isHeading field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withIsHeading(@Nonnull boolean isHeading) {
			this.isHeading = isHeading;
			return this;
		}

		/**
		 * Builder method for isFlavorAffinityEntries parameter.
		 * 
		 * @param isFlavorAffinityEntries field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withIsFlavorAffinityEntries(@Nonnull boolean isFlavorAffinityEntries) {
			this.isFlavorAffinityEntries = isFlavorAffinityEntries;
			return this;
		}

		/**
		 * Builder method for isQuote parameter.
		 * 
		 * @param isQuote field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withIsQuote(@Nonnull boolean isQuote) {
			this.isQuote = isQuote;
			return this;
		}

		/**
		 * Builder method for isAuthor parameter.
		 * 
		 * @param isAuthor field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withIsAuthor(@Nonnull boolean isAuthor) {
			this.isAuthor = isAuthor;
			return this;
		}

		/**
		 * Builder method for dishAttribute parameter.
		 * 
		 * @param dishAttribute field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withDishAttribute(@Nonnull DishAttribute dishAttribute) {
			this.dishAttribute = dishAttribute;
			return this;
		}

		/**
		 * Builder method for pairingLevel parameter.
		 * 
		 * @param pairingLevel field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withPairingLevel(@Nonnull PairingLevel pairingLevel) {
			this.pairingLevel = pairingLevel;
			return this;
		}

		/**
		 * Builder method for isEndingWithColon parameter.
		 * 
		 * @param isEndingWithColon field to set
		 * @return builder
		 */
		@Nonnull
		public Builder withIsEndingWithColon(@Nonnull boolean isEndingWithColon) {
			this.isEndingWithColon = isEndingWithColon;
			return this;
		}

		/**
		 * Builder method of the builder.
		 * 
		 * @return built class
		 */
		@Nonnull
		public EndOfTextCriteria build() {
			return new EndOfTextCriteria(this);
		}
	}
}
