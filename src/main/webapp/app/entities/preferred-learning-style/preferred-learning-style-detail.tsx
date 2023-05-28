import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './preferred-learning-style.reducer';

export const PreferredLearningStyleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preferredLearningStyleEntity = useAppSelector(state => state.preferredLearningStyle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preferredLearningStyleDetailsHeading">
          <Translate contentKey="eduApp.preferredLearningStyle.detail.title">PreferredLearningStyle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preferredLearningStyleEntity.id}</dd>
          <dt>
            <span id="style">
              <Translate contentKey="eduApp.preferredLearningStyle.style">Style</Translate>
            </span>
          </dt>
          <dd>{preferredLearningStyleEntity.style}</dd>
          <dt>
            <Translate contentKey="eduApp.preferredLearningStyle.educatorPreference">Educator Preference</Translate>
          </dt>
          <dd>{preferredLearningStyleEntity.educatorPreference ? preferredLearningStyleEntity.educatorPreference.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/preferred-learning-style" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/preferred-learning-style/${preferredLearningStyleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreferredLearningStyleDetail;
