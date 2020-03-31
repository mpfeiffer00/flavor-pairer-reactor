import React, { Component } from 'react';

class IngredientPairingComponent extends Component {
   constructor(props) {
      super(props)
      this.ingredient = this.props.ingredient;
      this.setState({item: []});
   }
   
   async componentDidMount() {
      const group = await (await fetch(`/api/flavorpairing/${this.ingredient}`)).json();
      this.setState({item: group});
   }

   render() {
      return (
         <h1>Flavor Pairings</h1>
      )
   }
}

export default withRouter(IngredientPairingComponent)