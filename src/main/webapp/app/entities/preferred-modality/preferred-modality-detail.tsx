import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './preferred-modality.reducer';

export const PreferredModalityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preferredModalityEntity = useAppSelector(state => state.preferredModality.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preferredModalityDetailsHeading">
          <Translate contentKey="eduApp.preferredModality.detail.title">PreferredModality</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preferredModalityEntity.id}</dd>
          <dt>
            <span id="modality">
              <Translate contentKey="eduApp.preferredModality.modality">Modality</Translate>
            </span>
          </dt>
          <dd>{preferredModalityEntity.modality}</dd>
          <dt>
            <Translate contentKey="eduApp.preferredModality.educatorPreference">Educator Preference</Translate>
          </dt>
          <dd>{preferredModalityEntity.educatorPreference ? preferredModalityEntity.educatorPreference.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/preferred-modality" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/preferred-modality/${preferredModalityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreferredModalityDetail;
