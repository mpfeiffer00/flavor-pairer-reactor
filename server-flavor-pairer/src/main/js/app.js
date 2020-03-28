'use strict';

const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

   constructor(props) {
      super(props);
      this.state = {ingredients: []};
   }

   componentDidMount() {
      fetch('/api/')
      .then(response => { return response.json() })
      .then(results => this.setState({ingredients: results}));  
   }

   render() {
     return (
       <IngredientList ingredients={this.state.ingredients}/>
     );
   }
}

class IngredientList extends React.Component{
   render() {  
      return (
         <ul>
           {this.props.ingredients.map(ingredient =>
             <li key={ingredient.toString()}>{ingredient}</li>
           )}
         </ul>
      )
   }
}

ReactDOM.render(
   <App />,
   document.getElementById('react')
)
