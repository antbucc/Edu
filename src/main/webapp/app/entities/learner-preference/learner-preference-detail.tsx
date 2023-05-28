import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './learner-preference.reducer';

export const LearnerPreferenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const learnerPreferenceEntity = useAppSelector(state => state.learnerPreference.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="learnerPreferenceDetailsHeading">
          <Translate contentKey="eduApp.learnerPreference.detail.title">LearnerPreference</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{learnerPreferenceEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="eduApp.learnerPreference.title">Title</Translate>
            </span>
          </dt>
          <dd>{learnerPreferenceEntity.title}</dd>
          <dt>
            <span id="style">
              <Translate contentKey="eduApp.learnerPreference.style">Style</Translate>
            </span>
          </dt>
          <dd>{learnerPreferenceEntity.style}</dd>
          <dt>
            <span id="modality">
              <Translate contentKey="eduApp.learnerPreference.modality">Modality</Translate>
            </span>
          </dt>
          <dd>{learnerPreferenceEntity.modality}</dd>
          <dt>
            <span id="difficulty">
              <Translate contentKey="eduApp.learnerPreference.difficulty">Difficulty</Translate>
            </span>
          </dt>
          <dd>{learnerPreferenceEntity.difficulty}</dd>
          <dt>
            <span id="disability">
              <Translate contentKey="eduApp.learnerPreference.disability">Disability</Translate>
            </span>
          </dt>
          <dd>{learnerPreferenceEntity.disability}</dd>
          <dt>
            <Translate contentKey="eduApp.learnerPreference.learner">Learner</Translate>
          </dt>
          <dd>{learnerPreferenceEntity.learner ? learnerPreferenceEntity.learner.surname : ''}</dd>
        </dl>
        <Button tag={Link} to="/learner-preference" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/learner-preference/${learnerPreferenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LearnerPreferenceDetail;
