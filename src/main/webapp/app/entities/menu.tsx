import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/scenario">
        <Translate contentKey="global.menu.entities.scenario" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/module">
        <Translate contentKey="global.menu.entities.module" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/learner">
        <Translate contentKey="global.menu.entities.learner" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/educator">
        <Translate contentKey="global.menu.entities.educator" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/domain">
        <Translate contentKey="global.menu.entities.domain" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/competence">
        <Translate contentKey="global.menu.entities.competence" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/concept">
        <Translate contentKey="global.menu.entities.concept" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/activity">
        <Translate contentKey="global.menu.entities.activity" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/fragment">
        <Translate contentKey="global.menu.entities.fragment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/precondition">
        <Translate contentKey="global.menu.entities.precondition" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/effect">
        <Translate contentKey="global.menu.entities.effect" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/goal">
        <Translate contentKey="global.menu.entities.goal" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/abstract-activity">
        <Translate contentKey="global.menu.entities.abstractActivity" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sequence">
        <Translate contentKey="global.menu.entities.sequence" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/order">
        <Translate contentKey="global.menu.entities.order" />
      </MenuItem>

      <MenuItem icon="asterisk" to="/set-of">
        <Translate contentKey="global.menu.entities.setOf" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/sequence-fragment">
        <Translate contentKey="global.menu.entities.sequenceFragment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/module-1">
        <Translate contentKey="global.menu.entities.module1" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
