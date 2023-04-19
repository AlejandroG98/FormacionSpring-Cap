import React, { Component } from 'react'
import Actores from '../Actores.js'

export default class GetAll extends Component {
  render() {
    return (
      <div>
        <h3>Hola esto es getAll</h3>


      </div>
    )
  }
}

// http://localhost:8001/actores/get
// @GetMapping(path = "/get")
// public @ResponseBody List<ActorShort> getActors(@RequestParam(required = false) String sort)
// 		throws JsonProcessingException {
// 	if (sort != null)
// 		return (List<ActorShort>) actService.getByProjection(Sort.by(sort), ActorShort.class);
// 	return actService.getByProjection(ActorShort.class);
// }
