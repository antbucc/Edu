import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './educator-preference.reducer';

export const EducatorPreferenceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const educatorPreferenceEntity = useAppSelector(state => state.educatorPreference.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="educatorPreferenceDetailsHeading">
          <Translate contentKey="eduApp.educatorPreference.detail.title">EducatorPreference</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{educatorPreferenceEntity.id}</dd>
          <dt>
            <span id="subject">
              <Translate contentKey="eduApp.educatorPreference.subject">Subject</Translate>
            </span>
          </dt>
          <dd>{educatorPreferenceEntity.subject}</dd>
          <dt>
            <span id="difficulty">
              <Translate contentKey="eduApp.educatorPreference.difficulty">Difficulty</Translate>
            </span>
          </dt>
          <dd>{educatorPreferenceEntity.difficulty}</dd>
          <dt>
            <Translate contentKey="eduApp.educatorPreference.eductarPreferences">Eductar Preferences</Translate>
          </dt>
          <dd>{educatorPreferenceEntity.eductarPreferences ? educatorPreferenceEntity.eductarPreferences.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/educator-preference" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/educator-preference/${educatorPreferenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EducatorPreferenceDetail;
