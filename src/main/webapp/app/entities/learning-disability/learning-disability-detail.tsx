import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './learning-disability.reducer';

export const LearningDisabilityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const learningDisabilityEntity = useAppSelector(state => state.learningDisability.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="learningDisabilityDetailsHeading">
          <Translate contentKey="eduApp.learningDisability.detail.title">LearningDisability</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{learningDisabilityEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="eduApp.learningDisability.name">Name</Translate>
            </span>
          </dt>
          <dd>{learningDisabilityEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="eduApp.learningDisability.description">Description</Translate>
            </span>
          </dt>
          <dd>{learningDisabilityEntity.description}</dd>
          <dt>
            <span id="disabilityType">
              <Translate contentKey="eduApp.learningDisability.disabilityType">Disability Type</Translate>
            </span>
          </dt>
          <dd>{learningDisabilityEntity.disabilityType}</dd>
          <dt>
            <Translate contentKey="eduApp.learningDisability.learnarDisabilities">Learnar Disabilities</Translate>
          </dt>
          <dd>{learningDisabilityEntity.learnarDisabilities ? learningDisabilityEntity.learnarDisabilities.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/learning-disability" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/learning-disability/${learningDisabilityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LearningDisabilityDetail;
