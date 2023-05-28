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
            <span id="title">
              <Translate contentKey="eduApp.educatorPreference.title">Title</Translate>
            </span>
          </dt>
          <dd>{educatorPreferenceEntity.title}</dd>
          <dt>
            <span id="style">
              <Translate contentKey="eduApp.educatorPreference.style">Style</Translate>
            </span>
          </dt>
          <dd>{educatorPreferenceEntity.style}</dd>
          <dt>
            <span id="modality">
              <Translate contentKey="eduApp.educatorPreference.modality">Modality</Translate>
            </span>
          </dt>
          <dd>{educatorPreferenceEntity.modality}</dd>
          <dt>
            <span id="difficulty">
              <Translate contentKey="eduApp.educatorPreference.difficulty">Difficulty</Translate>
            </span>
          </dt>
          <dd>{educatorPreferenceEntity.difficulty}</dd>
          <dt>
            <Translate contentKey="eduApp.educatorPreference.educator">Educator</Translate>
          </dt>
          <dd>{educatorPreferenceEntity.educator ? educatorPreferenceEntity.educator.surname : ''}</dd>
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
